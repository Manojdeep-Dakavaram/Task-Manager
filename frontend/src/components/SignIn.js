// SignInForm.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../Styles/SignUp.css';
import { ThreeDots } from 'react-loader-spinner';

const SignIn = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const serverURL = 'http://localhost:8080';

  const handleSignin = async() => {
    if (!email.trim() || !password.trim()) {
      setMessage('Enter all the fields');
      return;
    } else if (!isValidEmail(email)) {
        
      setMessage('Please enter a valid email');
      return;
    }
    else{
    setLoading(true);
    fetch(`${serverURL}/users/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ fname:'',lname:'', email, password }),
    })
    .then(response => {
      if (!response.ok) {
        setMessage('Could not Signin. Please try again')
        return;
      }
      return response.json(); // Parse the JSON response
    })
    .then(data => {
        sessionStorage.setItem('email', data.email);
        navigate('/home')
    })
    .catch(error => {
      console.error('Error:', error);
    });
    setLoading(false);
  }
  };

  const isValidEmail = (email) => {
    // Simple email format validation
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
  };


  return (
    <div className="wrapper">
    <h2>Login to your account</h2>
    <form>
      <div className="input-box">
        <input type="text" onChange={(e) => setEmail(e.target.value)} placeholder="Enter your email" required/>
      </div>
      <div className="input-box">
        <input type="password" onChange={(e) => setPassword(e.target.value)} placeholder="Create password" required/>
      </div>
      <div className="input-box-button">
      {loading ? (
             <ThreeDots color="#00BFFF" height={24} width={24} />
          ) : (
        <input onClick={handleSignin} defaultValue="Log In"/>
        )}
      </div>
      {message && <p style={{ whiteSpace: 'pre-wrap', marginBottom: '0', color:'red', fontWeight: 'bold' }} >{message}</p>}
      </form>
  </div>
  );
};

export default SignIn;
