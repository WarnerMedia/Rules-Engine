import { ErrorType } from '../types/external';
import { AbstractError } from './abstract-error';

export class EngineDefinitionError extends AbstractError {
    public constructor(message: string) {
        super(ErrorType.ENGINE_DEFINITION, message);

        Object.setPrototypeOf(this, EngineDefinitionError.prototype);
    }
}
