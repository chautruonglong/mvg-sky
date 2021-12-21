module.exports = {
  project: {
    ios: {},
    android: {}, // grouped into "project"
  },
  dependencies: {
    'react-native-fbsdk': {
      platforms: {
        ios: null,
      },
    },
  },
  assets: ['./assets/fonts/'], // stays the same
};
