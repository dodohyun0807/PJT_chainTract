import React from 'react';
import { Global, css } from '@emotion/react';
import reset from 'emotion-reset';

const GlobalStyle = () => (
  <Global
    styles={css`
      ${reset}

      html {
        font-size: 16px;
        margin: 0 auto;
      }

      body {
        letter-spacing: 0.08rem;
      }

      a {
        text-decoration: none;
        color: currentColor;
      }

      button {
        cursor: pointer;
      }
      input {
        cursor: pointer;
      }
    `}
  />
);

export default GlobalStyle;
