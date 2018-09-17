import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter} from 'react-router-dom';
import stores from './stores';
import {Provider} from 'mobx-react';
import MainRouter from './router/MainRouter';
import {MuiThemeProvider} from '@material-ui/core/styles';
import theme from './util/theme';

ReactDOM.render(
  <MuiThemeProvider theme={theme}>
    <Provider {...stores}>
      <React.Fragment>
        <BrowserRouter>
          <MainRouter />
        </BrowserRouter>
      </React.Fragment>
    </Provider>
  </MuiThemeProvider>,
  document.getElementById('root')
);
