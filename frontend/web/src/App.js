import './App.css';
import {BrowserRouter as Router, Switch, Route, Link} from "react-router-dom"
import { Header } from './components/Header';
import { Home  } from './components/Home';

function App() {
  return (
    <Router>
      <Switch>
        <Route exact path ="/">
          <Header/>
        </Route>

        <Route exact path ="/channels">
          <Home/>
        </Route>

        <Route exact path ="/channels/:id">
          <Home/>
        </Route>
      </Switch>
    </Router>
  );
}

export default App;
