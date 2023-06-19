import './App.css';
import 'dracula-ui/styles/dracula-ui.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import Header  from './components/Header/Header'
import Content from './components/Content/Content'

function App() {
  return (
    <div className="App">
        <Header></Header>
        <Content>test</Content>

    </div>
  );
}

export default App;
