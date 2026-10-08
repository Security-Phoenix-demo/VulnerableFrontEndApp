const path = require('path');

module.exports = {
  entry: './src/index.js',
  output: { path: path.resolve(__dirname, 'dist'), filename: 'bundle.js', publicPath: '/' },
  module: {
    rules: [
      { test: /\.jsx?$/, exclude: /node_modules/, use: 'babel-loader' },
      { test: /\.css$/, use: [ 'style-loader', 'css-loader' ] },
    ],
  },
  resolve: { extensions: [ '.js', '.jsx' ] },
  devServer: { historyApiFallback: true, port: 4300, proxy: { '/api': 'http://localhost:3000' } },
};
