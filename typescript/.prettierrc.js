module.exports = {
    printWidth: 100,
    tabWidth: 4,
    useTabs: false,
    singleQuote: true,
    trailingComma: 'all',
    bracketSpacing: true,
    overrides: [
        {
            files: '*.ts',
            options: {
                parser: 'typescript',
            },
        },
        {
            files: '*.json',
            options: {
                printWidth: 80,
                tabWidth: 2,
            },
        },
    ],
};
