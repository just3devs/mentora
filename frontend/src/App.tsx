import { store } from './app/store';
import AppRoutes from './routes';
import { Provider } from 'react-redux';


function App() {
    return (
      <Provider store={store}>
        <AppRoutes />
      </Provider>
    );
}

export default App;