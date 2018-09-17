import React from 'react';
import {Switch, Route} from 'react-router-dom';
import App from './../container/App';

class MainRouter extends React.Component {
  render() {
    return (
      <Switch>
        <Route path="/" component={App} />
      </Switch>
    );
  }
}

export default MainRouter;
