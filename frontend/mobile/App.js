import React from 'react';
import { SafeAreaView } from "react-native"
import Providers from './navigation';
import Toast from 'react-native-toast-message';

console.disableYellowBox = true;

const App = () => {
  return (
    <>
      <Providers />
      <Toast />
    </>
  );
}

export default App;
