import './App.css';
import {BrowserRouter as Router, Switch, Route, Link} from "react-router-dom"
import { Header } from './components/Header';

function App() {
  return (
    <Router>
      <Switch>
        <Route exact path ="/">
          <Header/>
        </Route>
      </Switch>
    </Router>
  );
}

export default App;
