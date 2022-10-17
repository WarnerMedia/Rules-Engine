import { MD5, sha1 } from 'object-hash';

import {
    IConditionEvaluationOptions,
    IConditionOptions,
    IGNORE_UNDEFINED_AS_NOT_APPLICABLE,
    IMemoizedData,
    JSONValue,
} from '../types/external';
import { ExtractElementType, ICondition, IConditionOperator } from '../types/internal';
import { getJsonPathElement } from '../utils';

export class Condition<T extends Readonly<string[]>> implements ICondition {
    public readonly fact: ExtractElementType<T>;
    public readonly operator: IConditionOperator;
    public readonly options: IConditionOptions;
    public readonly memoizedName: string;

    /**
     * Create a new condition
     * @param fact Name of the fact to use for the condition
     * @param operator Operator used to evaluate the condition
     * @param options Optional properties for the condition
     */
    public constructor(
        fact: ExtractElementType<T>,
        operator: IConditionOperator,
        options?: Partial<IConditionOptions>,
    ) {
        this.fact = fact;
        this.operator = operator;
        this.options = {
            ignoreUndefinedAs: options?.ignoreUndefinedAs ?? IGNORE_UNDEFINED_AS_NOT_APPLICABLE,
            useSHA1ForMemoizedName: options?.useSHA1ForMemoizedName ?? false,
        };
        const dataForMemoization = {
            fact,
            operator,
            options: this.options,
        };
        this.memoizedName = this.options.useSHA1ForMemoizedName
            ? sha1(dataForMemoization)
            : MD5(dataForMemoization);
    }

    /**
     * Evaluate the facts against condition
     * @param facts Information to evaluate the condition against
     * @param memoizedData Memoized data to use for condition evaluation
     * @param options Options to use for condition evaluation
     */
    public evaluate(
        facts: Record<string, JSONValue>,
        memoizedData: IMemoizedData,
        options: IConditionEvaluationOptions,
    ): boolean {
        const memoizedResult = this.getMemoizedResult(
            memoizedData.conditionResults,
            options.memoizeConditionResult,
        );
        const result = memoizedResult ?? this.calculateResult(facts);

        if (options.memoizeConditionResult && memoizedResult === undefined) {
            memoizedData.conditionResults[this.memoizedName] = result;
        }

        return result;
    }

    private calculateResult(facts: Record<string, JSONValue>): boolean {
        const factValue = getJsonPathElement(this.fact, facts);

        if (factValue === undefined) {
            return this.options.ignoreUndefinedAs !== IGNORE_UNDEFINED_AS_NOT_APPLICABLE
                ? this.options.ignoreUndefinedAs
                : false;
        }

        return this.operator.evaluate(factValue);
    }

    private getMemoizedResult(
        memoizedConditionResults: Record<string, boolean>,
        memoizeConditionResult: boolean,
    ): boolean | undefined {
        if (memoizeConditionResult) {
            return memoizedConditionResults[this.memoizedName];
        }

        return undefined;
    }
}
