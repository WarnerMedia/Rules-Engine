import {
    ArrayContainsOperator,
    ConditionOperator,
    EqualsOperator,
    GreaterThanInclusiveOperator,
    GreaterThanOperator,
    InRangeOperator,
    IsDefinedOperator,
    LessThanInclusiveOperator,
    LessThanOperator,
    NotArrayContainsOperator,
    NotEqualsOperator,
    NotInRangeOperator,
    StringContainsOperator,
} from '../../src';
import { IConditionJsonRepresentation, IConditionOperator, IRange } from '../../src/types/internal';
import { mapJsonToCondition } from '../../src/utils';

describe('utils', () => {
    beforeEach(() => {
        jest.clearAllMocks();
        jest.resetModules();
    });

    function getCondition(
        operatorType: ConditionOperator,
        value: string | number[] | IRange,
    ): IConditionJsonRepresentation {
        return {
            fact: 'factA',
            operator: {
                operatorType,
                valueToCompareAgainst: value,
            } as IConditionOperator,
            options: { ignoreUndefinedAs: false, useSHA1ForMemoizedName: false },
        };
    }

    describe('mapJsonToCondition', () => {
        test('should create equals condition', () => {
            const result = mapJsonToCondition(getCondition(ConditionOperator.EQUALS, 'abcde'));
            expect(result.operator.operatorType).toEqual(ConditionOperator.EQUALS);
            expect(result.operator).toEqual(expect.any(EqualsOperator));
            expect(result.operator.valueToCompareAgainst).toEqual('abcde');
        });

        test('should create not equals condition', () => {
            const result = mapJsonToCondition(getCondition(ConditionOperator.NOT_EQUALS, 'abcde'));
            expect(result.operator.operatorType).toEqual(ConditionOperator.NOT_EQUALS);
            expect(result.operator).toEqual(expect.any(NotEqualsOperator));
            expect(result.operator.valueToCompareAgainst).toEqual('abcde');
        });

        test('should create greater than inclusive condition', () => {
            const result = mapJsonToCondition(
                getCondition(ConditionOperator.GREATER_THAN_INCLUSIVE, 'abcde'),
            );
            expect(result.operator.operatorType).toEqual(ConditionOperator.GREATER_THAN_INCLUSIVE);
            expect(result.operator).toEqual(expect.any(GreaterThanInclusiveOperator));
            expect(result.operator.valueToCompareAgainst).toEqual('abcde');
        });

        test('should create greater than condition', () => {
            const result = mapJsonToCondition(
                getCondition(ConditionOperator.GREATER_THAN, 'abcde'),
            );
            expect(result.operator.operatorType).toEqual(ConditionOperator.GREATER_THAN);
            expect(result.operator).toEqual(expect.any(GreaterThanOperator));
            expect(result.operator.valueToCompareAgainst).toEqual('abcde');
        });

        test('should create less than condition', () => {
            const result = mapJsonToCondition(getCondition(ConditionOperator.LESS_THAN, 'abcde'));
            expect(result.operator.operatorType).toEqual(ConditionOperator.LESS_THAN);
            expect(result.operator).toEqual(expect.any(LessThanOperator));
            expect(result.operator.valueToCompareAgainst).toEqual('abcde');
        });

        test('should create less than inclusive condition', () => {
            const result = mapJsonToCondition(
                getCondition(ConditionOperator.LESS_THAN_INCLUSIVE, 'abcde'),
            );
            expect(result.operator.operatorType).toEqual(ConditionOperator.LESS_THAN_INCLUSIVE);
            expect(result.operator).toEqual(expect.any(LessThanInclusiveOperator));
            expect(result.operator.valueToCompareAgainst).toEqual('abcde');
        });

        test('should create not in range condition', () => {
            const result = mapJsonToCondition(
                getCondition(ConditionOperator.NOT_IN_RANGE, { start: 5, end: 10 }),
            );
            expect(result.operator.operatorType).toEqual(ConditionOperator.NOT_IN_RANGE);
            expect(result.operator).toEqual(expect.any(NotInRangeOperator));
            expect(result.operator.valueToCompareAgainst).toEqual({ start: 5, end: 10 });
        });

        test('should create in range condition', () => {
            const result = mapJsonToCondition(
                getCondition(ConditionOperator.IN_RANGE, { start: 5, end: 10 }),
            );
            expect(result.operator.operatorType).toEqual(ConditionOperator.IN_RANGE);
            expect(result.operator).toEqual(expect.any(InRangeOperator));
            expect(result.operator.valueToCompareAgainst).toEqual({ start: 5, end: 10 });
        });

        test('should create array contains condition', () => {
            const result = mapJsonToCondition(
                getCondition(ConditionOperator.ARRAY_CONTAINS, [1, 2]),
            );
            expect(result.operator.operatorType).toEqual(ConditionOperator.ARRAY_CONTAINS);
            expect(result.operator).toEqual(expect.any(ArrayContainsOperator));
            expect(result.operator.valueToCompareAgainst).toEqual([1, 2]);
        });

        test('should create string contains condition', () => {
            const result = mapJsonToCondition(
                getCondition(ConditionOperator.STRING_CONTAINS, 'abcde'),
            );
            expect(result.operator.operatorType).toEqual(ConditionOperator.STRING_CONTAINS);
            expect(result.operator).toEqual(expect.any(StringContainsOperator));
            expect(result.operator.valueToCompareAgainst).toEqual('abcde');
        });

        test('should create not array contains condition', () => {
            const result = mapJsonToCondition(
                getCondition(ConditionOperator.NOT_ARRAY_CONTAINS, [1, 2]),
            );
            expect(result.operator.operatorType).toEqual(ConditionOperator.NOT_ARRAY_CONTAINS);
            expect(result.operator).toEqual(expect.any(NotArrayContainsOperator));
            expect(result.operator.valueToCompareAgainst).toEqual([1, 2]);
        });

        test('should create is defined condition', () => {
            const result = mapJsonToCondition(getCondition(ConditionOperator.IS_DEFINED, 'x'));
            expect(result.operator).toEqual(expect.any(IsDefinedOperator));
            expect(result.operator.operatorType).toEqual(ConditionOperator.IS_DEFINED);
            expect(result.operator.valueToCompareAgainst).toEqual('x');
        });

        test('should throw error for unrecognized operator', () => {
            const result = () =>
                mapJsonToCondition(getCondition('does_not_exist' as ConditionOperator, 'abcde'));

            expect(result).toThrowError('operator not supported: does_not_exist');
        });
    });
});
