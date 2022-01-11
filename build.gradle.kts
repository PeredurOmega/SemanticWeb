plugins {
    kotlin("js") version "1.6.10"
    id("com.github.turansky.kfc.webpack") version "4.63.0"
}

group = "fr.insa"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

fun kotlinw(target: String): String =
    "org.jetbrains.kotlin-wrappers:kotlin-$target"

dependencies {
    testImplementation(kotlin("test"))
    implementation(enforcedPlatform(kotlinw("wrappers-bom:0.0.1-pre.290-kotlin-1.6.10")))

    //React
    implementation(kotlinw("react"))
    implementation(kotlinw("react-dom"))
    implementation(kotlinw("react-router-dom"))

    //Inline svg
    implementation(npm("react-inlinesvg", "2.3.0"))

    //Utility dev npm dependencies
    implementation(devNpm("zlib", "1.0.5"))
    implementation(devNpm("path", "0.12.7"))
    implementation(devNpm("postcss", "8.4.5"))

    //Code optimizations
    implementation(devNpm("terser-webpack-plugin", "5.3.0"))
    implementation(devNpm("compression-webpack-plugin", "9.2.0"))
    implementation(devNpm("@gfx/zopfli", "1.0.15"))

    //SAAS
    implementation(devNpm("sass", "1.45.2"))
    implementation(devNpm("sass-loader", "12.4.0"))
    implementation(devNpm("postcss-loader", "6.2.1"))
    implementation(devNpm("autoprefixer", "10.4.1"))
}

kotlin {
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
                val devServerPort: String? by project
                devServer = org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.DevServer(
                    port = devServerPort?.toIntOrNull() ?: 3000,
                    static = mutableListOf("$buildDir/processedResources/js/main")
                )
            }
            //Dev task
            runTask {
                sourceMaps = true
            }
        }
    }
}

tasks {
    patchWebpackConfig {
        // language=JavaScript
        patch(
            """
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
                        test: /\.(js|css|html|svg)${'$'}/,
                        compressionOptions: {
                            params: {
                                [zlib.constants.BROTLI_PARAM_QUALITY]: 11,
                            },
                        },
                        threshold: 10240,
                        minRatio: 0.8
                    }),
                    new CompressionPlugin({
                        test: /\.(js|css|html|svg)${'$'}/,
                        filename: '[path][base].gz',
                        algorithm: gzip,
                        threshold: 10240,
                        minRatio: 0.8
                    })];
            }
            
            config.module.rules.push({
                test: /\.s[ac]ss${'$'}/i,
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
            
            //TODO ADD FOR BROWSER ROUTER USAGE config.output.publicPath = '/';
            
            config.resolve.modules.push("processedResources/js/main");
        """.trimIndent()
        )
    }
}