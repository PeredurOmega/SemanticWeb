// chunk-name
;(function (config) {
if (!!config.output) 
  config.output.chunkFilename = '[name].[contenthash].js'
})(config)

// generated_3
;(function (config) {
const TerserPlugin = require('terser-webpack-plugin');
const CompressionPlugin = require('compression-webpack-plugin');
const {gzip} = require('@gfx/zopfli');
const zlib = require('zlib');
const autoprefixer = require('autoprefixer');
const webpack = require('webpack')

if (config.mode === "production") {
    console.info("=====> Compressing and minifying <=====");
    config.optimization = {
        minimize: true,
        minimizer: [new TerserPlugin()],
    };
    config.plugins = [
        new CompressionPlugin({
            filename: '[path][base].br',
            algorithm: 'brotliCompress',
            test: /\.(js|css|html|svg)$/,
            compressionOptions: {
                params: {
                    [zlib.constants.BROTLI_PARAM_QUALITY]: 11,
                },
            },
            threshold: 10240,
            minRatio: 0.8
        }),
        new CompressionPlugin({
            test: /\.(js|css|html|svg)$/,
            filename: '[path][base].gz',
            algorithm: gzip,
            threshold: 10240,
            minRatio: 0.8
        })];
}

config.module.rules.push({
    test: /\.s[ac]ss$/i,
    use: [
        //Creates `style` nodes from JS strings
        'style-loader',
        //Translates CSS into CommonJS
        {
            loader: 'css-loader',
            options: {
                importLoaders: 1
            }
        },
        //Improves styles with post processing
        {
            loader: 'postcss-loader',
            options: {
                postcssOptions: {
                    plugins: [autoprefixer()]
                    //TODO MIGRATE FROM SASS TO ONLY POSTCSS (https://simonecorsi.medium.com/moving-from-sass-to-postcss-why-what-and-how-f68b1bc760dc)
                    //TODO EXPLORE THE POSSIBILITIES AT https://github.com/postcss/postcss#plugins
                }
            }
        },
        //Compiles Sass to CSS
        'sass-loader',
    ],
});

config.plugins.push(new webpack.ProvidePlugin({
    process: 'process/browser',
}));

config.output.publicPath = '/';

config.resolve.modules.push("processedResources/js/main");
})(config)

// resources
;(function (config) {
config.resolve.modules.unshift(
    'D:\\Cours INSA\\WebSemantic\\src\\main\\resources'
)
})(config)