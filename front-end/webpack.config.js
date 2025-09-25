import path from 'path'
import CopyPlugin from 'copy-webpack-plugin';
import { fileURLToPath } from 'url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

export default {
    mode: 'development',
    entry: './src/main.ts',
    devServer: {
        static: {
            directory: path.join(__dirname, 'dist'),
        },
        watchFiles: ['public/**/*'],
        port: 9000,
        hot: false,
        liveReload: true,
        open: true
    },
    output: { 
        filename: 'app.min.js',
        path: path.resolve(__dirname, 'dist'),
        clean: true
    },
    plugins :[
        new CopyPlugin({
            patterns: [{from: "public"}]
        })
    ],
    resolve: { 
        extensions: ['.ts', '.js']
    },
    module : { 
        rules: [{
            test: /\.ts$/,
            use: 'ts-loader',
            exclude: /node_modules/
        }]
    }
}