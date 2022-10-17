import { Rule } from '../../engine';
import { NonNullableJSONValue } from '../../types/external';
import { IRule } from '../../types/internal';

/**
 * Create a new rule from existing rule with `negateEvaluation` turned on
 * @param rule Existing rule to use conditions from
 * @param options Optional override for name and result
 */
export function notRule(
    rule: IRule,
    options?: {
        name?: string;
        result?: [NonNullableJSONValue, NonNullableJSONValue];
    },
): IRule {
    return new Rule(
        options?.name ?? 'not-' + rule.name,
        rule.conditions,
        options?.result ?? rule.result,
        { ...rule.options, negateEvaluation: true },
    );
}
