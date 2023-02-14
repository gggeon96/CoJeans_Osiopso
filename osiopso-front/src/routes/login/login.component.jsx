import { useState } from 'react'
import { useNavigate } from "react-router-dom";
import FormInput from '../../components/form-input/form-input.component'
import Button from '../../components/button/button.component'
import { SignInContainer, ButtonsContainer, Osiopso, Bodoni, FindIdPassword  } from './login.stlyes'
import { useDispatch } from 'react-redux'
import './login.stlyes'
import axios from 'axios';
import { login } from '../../store/user/user.reducer'
// import {
//   signInAuthUserWithEmailAndPassword,
//   signInWithGooglePopup,
// } from '../../utils/firebase/firebase.utils';


const defaultFormFields = {
  email: '',
  password: '',
};


const Login = () => {
  const [formFields, setFormFields] = useState(defaultFormFields);
  const { email, password } = formFields;


  const resetFormFields = () => {
    setFormFields(defaultFormFields);
  };

  // const signInWithGoogle = async () => {
  //   await signInWithGooglePopup();
  // };

  // const handleSubmit = async (event) => {
  //   event.preventDefault();

  //   try {
  //     await signInAuthUserWithEmailAndPassword(email, password);
  //     resetFormFields();
  //   } catch (error) {
  //     console.log('user sign in failed', error);
  //   }
  // };

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormFields({ ...formFields, [name]: value });
  };
  const dispatch = useDispatch()
  const [loading, setLoading] = useState(false);
  const [msg, setMsg] = useState("");
  
  const navigate = useNavigate()
  const LoginFunc = (e) => {
    e.preventDefault();
      axios({
        method: "post",
        url: `${process.env.REACT_APP_AXIOS_URL}user/login`,
        data: {
          email,
          password,
        }
      })
      .then((res)=>{
        console.log(res)
        localStorage.clear()
        localStorage.setItem('token', res.data.accessToken)
        localStorage.setItem('email', email)
        
        const value = {email, token: res.data.accessToken}
        dispatch(login(value))
        navigate("/")
      })
      .catch((err) => {
        console.log(err)
        navigate("/login")
      })
  }

  const SignUp = ()=>{
    navigate('/join')
  }

  const goToFindPage = ()=> {
    
  }
  return (
    <SignInContainer>

      <hr />
      {/* <h2>Already have an account?</h2> */}
      {/* <span>이메일과 비밀번호를 입력하세요.</span> */}
      <Osiopso>
        <Bodoni>Osiopso</Bodoni>
        &nbsp;내 옷장을 앱속으로
        </Osiopso>
        <FormInput
          label='Email'
          type='email'
          required
          onChange={handleChange}
          name='email'
          value={email}
        />

        <FormInput
          label='Password'
          type='password'
          required
          onChange={handleChange}
          name='password'
          value={password}
        />
        
        <ButtonsContainer>
        <Button
          type='submit'
          size={'md'}
          variant={ 'success'}
          onClick={LoginFunc}
          >Sign In</Button>

        <Button
          type='submit'
          size={'md'}
          variant={ 'success'}
          onClick={SignUp}
          >Join</Button>
        
        </ButtonsContainer>
        <p onClick={goToFindPage}>아이디/비밀번호 찾기 ></p>
    </SignInContainer>
  );
};

export default Login;