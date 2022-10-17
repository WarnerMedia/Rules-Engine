import { ErrorType } from '../types/external';
import { AbstractError } from './abstract-error';

export class EngineEvaluationError extends AbstractError {
    public constructor(message: string) {
        super(ErrorType.ENGINE_EVALUATION, message);

        Object.setPrototypeOf(this, EngineEvaluationError.prototype);
    }
}
