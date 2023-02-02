import {Routes, Route} from 'react-router-dom'

import MypageBody from '../../components/mypage/mypage.component'
import ClothesAdd from '../../components/clothes-add/clothes-add.component'
import ClosetDetail from '../../components/closet-detail/closet-detail.component'

const Mypage = () => {
	return (
		<Routes>
			<Route index element={<MypageBody/>} />
			<Route path="/add-clothes" element={<ClothesAdd />} />
			<Route path="/closet/:closetName" element={ <ClosetDetail/>} />
		</Routes>
		
	)
}

export default Mypage