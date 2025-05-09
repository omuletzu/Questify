module.exports = {
  testEnvironment: "jsdom", // Simulează un browser
  setupFilesAfterEnv: ["@testing-library/jest-dom/extend-expect"], // Extinde matcherele Jest
  moduleNameMapper: {
    "\\.(css|less|scss|sass)$": "identity-obj-proxy", // Mock pentru stiluri
  },
};