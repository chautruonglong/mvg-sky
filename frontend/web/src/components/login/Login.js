import { useState } from 'react'
import { useHistory } from 'react-router'
import authApi from '../../api/authApi'
import './login.css'

export const Login = () => {
	let history = useHistory()
	const [email, setEmail] = useState('')
	const [password, setPassword] = useState('')

	const HandleClickSignIn = async () => {
		// try {
		// 	const response = await authApi.login({email, password})
		// 	console.log('response', response);
		// 	history.push('/channels')
		// } catch (error) {
		// 	console.log("fail to login");
		// }

		history.push('/channels')
	}

    return (
    <>        
		<div className="wrapper">
			<div className="form">
			<div id="btn" className="text-center-logo">
			<img src="https://scontent.xx.fbcdn.net/v/t1.15752-9/p206x206/259344100_221240166797176_7169315392320620239_n.png?_nc_cat=111&ccb=1-5&_nc_sid=aee45a&_nc_ohc=zqPNQwwlklYAX9X-JZH&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=5530f23c2869d704388b1f8249a048f8&oe=61C022C4" alt="" className="h-12 cursor-pointer rounded-full transition-all duration-100 ease-out hover:rounded-2xl" />
   			</div>
				<div className="title">
					MVG-SKY
				</div>
					<div className="input_wrap">
						<label htmlFor="input_text">Email or Username</label>
						<div className="input_field">
							<input type="text" className="input" id="input_text" onChange={(e)=> setEmail(e.target.value)}/>
						</div>
					</div>
					<div className="input_wrap">
						<label htmlFor="input_password">Password</label>
						<div className="input_field">
							<input type="password" className="input" id="input_password" onChange={(e) => setPassword(e.target.value)}/>
						</div>
					</div>
					<div className="input_wrap">
						<input type="submit"  className="btn" value="Sign In" onClick={()=> HandleClickSignIn()}/>
					</div>
			</div>
		</div>
    </>
    )
}
