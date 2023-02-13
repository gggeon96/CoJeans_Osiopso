import { useLocation, useNavigate } from "react-router-dom"
import { useEffect, useState } from "react"
import { Fragment } from "react"
import {
	TopBarContainer,
	TopBarContent,
	ButtonContainer,
	CategoryContainer
} from "./top-bar.styles"

import { ReactComponent as BackButton } from '../../assets/back.svg'
import { SideBar } from "../side-bar/side-bar.component"


const TopBar = () => {
	const navigate = useNavigate();

	const [topName, setTopName] = useState('Osiopso')
	const location = useLocation()
	useEffect(() => {
		switch (location.pathname) {
			case '/join':
				setTopName('회원가입')
				break
			case '/login':
				setTopName('로그인')
				break
			case '/mypage/add-clothes':
				setTopName('옷 등록')
				break
			case '/ootd/create':
				setTopName('OOTD 등록')
				break
			case '/advice/create':
				setTopName('훈수 등록')
				break
			default:
				setTopName('Osiopso')
		}
}, [location ])

	return (
		<Fragment>
		{
				topName ==='Osiopso'?
		<TopBarContainer page={'two'}>
			<div>
			</div>
			<TopBarContent>
				{ topName }
			</TopBarContent>
			<CategoryContainer >
        		<SideBar/>
			</CategoryContainer>
		</TopBarContainer>
			:<TopBarContainer>
			<ButtonContainer onClick={() =>{
				navigate(-1)
			}}>
				<BackButton />
			</ButtonContainer>	

			<TopBarContent>
				{ topName }
			</TopBarContent>

		</TopBarContainer>
	}</Fragment>
	)
}

export default TopBar
