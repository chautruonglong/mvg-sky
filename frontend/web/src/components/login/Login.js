import './login.css'

export const Login = () => {
    return (
    <>        
		{/* <div class="container" id="container">
			<div class="form-container sign-in-container">
				<form action="#">
					<h1>Sign in</h1>
					<input class="input-login" type="email" placeholder="Email" />
					<input class="input-login"  type="password" placeholder="Password" />
					<button>Sign In</button>
				</form>
			</div>
			<div class="overlay-container">
				<div class="overlay">
					<div class="overlay-panel overlay-right">
						<h1>Hello, Friend!</h1>
						<p>Join our workplace</p>
					</div>
				</div>
			</div>
		</div> */}

		<div class="wrapper">
			<div class="form">
			<div id="btn" class="text-center-logo">
			<img src="https://scontent.xx.fbcdn.net/v/t1.15752-9/p206x206/259344100_221240166797176_7169315392320620239_n.png?_nc_cat=111&ccb=1-5&_nc_sid=aee45a&_nc_ohc=zqPNQwwlklYAX9X-JZH&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=5530f23c2869d704388b1f8249a048f8&oe=61C022C4" alt="" className="h-12 cursor-pointer rounded-full transition-all duration-100 ease-out hover:rounded-2xl" />
   			</div>
				<div class="title">
					MVG-SKY
				</div>
				<form method="post" action="successpage.html" onsubmit="return validation();">
					<div class="input_wrap">
						<label for="input_text">Email or Username</label>
						<div class="input_field">
							<input type="text" class="input" id="input_text"/>
						</div>
					</div>
					<div class="input_wrap">
						<label for="input_password">Password</label>
						<div class="input_field">
							<input type="password" class="input" id="input_password"/>
						</div>
					</div>
					<div class="input_wrap">
						<span class="error_msg">Incorrect username or password. Please try again</span>
						<input type="submit" id="login_btn" class="btn disabled" value="Sign In" disabled="true"/>
					</div>
				</form>
			</div>
		</div>
    </>
    )
}
