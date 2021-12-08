import './App.css';
import {BrowserRouter as Router, Switch, Route} from "react-router-dom"
import { Login } from './components/login/Login';
import { Home  } from './components/Home';

function App() {
  return (
    <Router>
      <Switch>
        <Route exact path ="/">
          <Login/>
        </Route>

        <Route exact path ="/channels">
          <Home/>
        </Route>

        <Route exact path ="/channels/chat">
          <Home status="chat"/>
        </Route>
        <Route exact path ="/channels/profile">
          <Home status="profile"/>
        </Route>
        <Route exact path ="/channels/email">
          <Home status="email"/>
        </Route>
      </Switch>
    </Router>
  );
}

export default App;
