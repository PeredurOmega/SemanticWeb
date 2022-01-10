if (config.output) {
    config.output.publicPath = '/';

    if (config.mode !== "production") {
        // Makes routing work on localhost
        config.devServer.historyApiFallback = true;
    } else {
        //Disable size warning (exceeded due to source map)
        config.performance = {
            hints: false,
            maxEntrypointSize: 512000,
            maxAssetSize: 512000
        }
    }
}