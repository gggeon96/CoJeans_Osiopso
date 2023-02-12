import {Routes, Route} from 'react-router-dom'
import MypageBody from '../../components/mypage/mypage.component'
import ClothesAdd from '../../components/clothes-add/clothes-add.component'
import ClosetDetail from '../../components/closet-detail/closet-detail.component'
import CameraPage from '../../components/clothes-add-camera/clothes-add-camera.component'
// import ClothesSelectBox from '../../components/clothes-select-box/clothes-select-box.component'
import ClothesSelectEdit from '../../components/clothes-select-edit/clothes-select-edit.component'
const Mypage = () => {
	return (
		<Routes>
			<Route index element={<MypageBody/>} />
			<Route path="/add-clothes" element={<ClothesAdd />} />
			<Route path="/add-clothes/camera" element={<CameraPage />} />
			{/* <Route path="/add-clothes/selectbox" element={<ClothesSelectBox />} /> */}
			<Route path="/add-clothes/selectbox/update" element={<ClothesSelectEdit />} />
			<Route path="/closet/:closetName" element={ <ClosetDetail/>} />
		</Routes>
		
	)
}

export default Mypage