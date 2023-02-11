import { createSlice } from '@reduxjs/toolkit';


export const user = createSlice({
  name: "user",
  initialState: {
    value: {
      email: "",
      token: "",
    },
    user: {
      id: "",
      name: "",
      age: "",
      gender: "",
      imageUrl: "",
    },
  },
  reducers: {
    login: (state, action) => {
      state.value = action.payload;
    },
    userInfo: (state, action) => {
      state.user = action.payload;
    },
    resetUser(state) {
      Object.assign(state.value, this.initialState.value);
    },
  },
});

export const { login, userInfo, resetUser } = user.actions;
export default user.reducer;

