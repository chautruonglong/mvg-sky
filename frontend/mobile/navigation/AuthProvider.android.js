import React, { createContext, useState } from 'react';
import apiRequest from "../utils/apiRequest"
import Toast from 'react-native-toast-message';


export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  return (
    <AuthContext.Provider
      value={{
        user,
        setUser,
        login: async (email, password) => {
          try {
            const response = await apiRequest.post('/accounts/login', {
              email: email,
              password: password
            })
            setUser(response)
            Toast.show({
              type: 'success',
              text2: 'Login successfully!'
            });
          } catch (error) {
            console.log(error)
            setUser(null)
            Toast.show({
              type: 'error',
              text1: 'Wrong username or password.'
            });
          }

        },

        logout: async () => {
          try {
            // const response = await apiRequest.delete(`/accounts/${user.accountEntity.id}/logout`)
            // const response = await apiRequest.delete(`/accounts/${user.accountEntity.id}/logout`, {
            //   refreshToken: "string"
            // })
            const response = await apiRequest.delete(`/accounts/${user.accountEntity.id}/logout`, {
              data: {
                refreshToken: "string"
              }
            })

            console.log(response)
            Toast.show({
              type: 'success',
              text2: 'Logout successfully!'
            });
            setUser(null)
          } catch (error) {
            console.log("vien")
            console.log(error)
            Toast.show({
              type: 'error',
              text1: 'Logout failed.'
            });
          }
        },
      }}>
      {children}
    </AuthContext.Provider>
  );
};
