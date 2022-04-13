module.exports = {
  parser: '@babel/eslint-parser',
  parserOptions: {
    requireConfigFile: false,
    babelOptions: {
      // presets: ['@babel/preset-react'],
      presets: ['@babel/react'],
    },
  },
  plugins: ['react', 'react-hooks'],

  extends: [
    'airbnb',
    'plugin:react/recommended',
    'plugin:jsx-a11y/recommended',
    'plugin:import/errors',
    'plugin:import/warnings',
    'plugin:prettier/recommended',
    'eslint:recommended',
  ],
  rules: {
    'prettier/prettier': 0,
    'no-use-before-define': 0,
    'react/jsx-filename-extension': 0,
    'import/extensions': 0,
    'import/no-unresolved': 0,
    'react/prop-types': 0,
    'import/order': 'off',
    'react/function-component-definition': 'off',
    'no-nested-ternary': 'off',
    'import/prefer-default-export': 'off',
    'react/jsx-no-useless-fragment': 'off',
    'react/jsx-props-no-spreading': 'off',
    'react/require-default-props': 'off',
    'import/no-extraneous-dependencies': 'off',
  },
  settings: {
    'import/resolver': {
      node: {
        extensions: ['.js', '.jsx'],
      },
      alias: {
        map: [['@', './src']],
      },
    },
  },
  globals: {
    window: false,
    sessionStorage: false,
  },
};
