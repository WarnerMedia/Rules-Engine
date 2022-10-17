import { ErrorType } from '../types/external';

export abstract class AbstractError extends Error {
    public override name: ErrorType;

    protected constructor(name: ErrorType, message: string) {
        super(message);

        this.name = name;
    }
}
