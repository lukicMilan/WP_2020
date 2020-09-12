module.exports = {
    devServer: {
        port:8081,
        proxy: {
            '^/': {
                target: 'http://localhost:8080/PocetniREST/rest',
                changeOrigin: true,
                secure: false,
                logLevel: 'debug'
            },
        }
    }
};