module.exports = {
    clearMocks: true,
    collectCoverageFrom: ['src/**/*.ts', '!src/**/index.ts', '!src/**/types.ts'],
    coverageReporters: ['cobertura', 'html', 'text-summary', 'text'],
    coverageThreshold: {
        global: {
            branches: 100,
            functions: 100,
            lines: 100,
            statements: 100,
        },
    },
    reporters: ['default', ['jest-junit', { outputDirectory: 'reports' }]],
    testEnvironment: 'node',
    transform: {
        '^.+\\.tsx?$': ['@swc/jest'],
    },
};
