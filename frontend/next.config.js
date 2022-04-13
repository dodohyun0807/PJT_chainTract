const Dotenv = require('dotenv-webpack');

module.exports = {
  reactStrictMode: true,

  webpack: (config) => {
    config.plugins.push(new Dotenv({ silent: true }));

    return config;
  },
  pageExtensions: ['jsx', 'js'],
};
