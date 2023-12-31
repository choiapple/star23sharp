import { useEffect } from 'react'
import Image from 'next/image'
import {
  ImageBox,
  KakaoContainer,
  LinkButton,
  TextBox,
} from './kakaoShareButon.styled'

export default function KaKaoShareButton() {
  // 현재 페이지 URL 저장, 이는 공유 버튼 클릭시 열리는 페이지의 주소로 사용됨
  const shareUrl = typeof window !== 'undefined' ? window.location.href : ''
  // useEffect를 이용하여 컴포넌트 렌더링시 카카오 SDK 초기화 및 공유 버튼 생성
  useEffect(() => {
    if (typeof window !== 'undefined' && window.Kakao) {
      const { Kakao } = window
      if (!Kakao.isInitialized()) {
        // SDK 초기화 부분, 본인의 API_KEY 입력
        Kakao.init(process.env.NEXT_PUBLIC_KAKAO_API_KEY)
      }
      Kakao.Link.createDefaultButton({
        // #kakao-link-btn id를 가진 요소에 공유 버튼을 생성하도록 함
        container: '#kakao-link-btn',
        objectType: 'feed',
        content: {
          title: '별이삼샵',
          description: '나에게 메시지를 적어줄래요?',
          imageUrl:
            'https://star23shop-bucket.s3.ap-northeast-2.amazonaws.com/image.png',
          link: {
            mobileWebUrl: shareUrl,
            webUrl: shareUrl,
          },
        },
        buttons: [
          {
            title: '메시지 남기러가기',
            link: {
              webUrl: shareUrl,
              mobileWebUrl: shareUrl,
            },
          },
        ],
      })
    }
  }, [shareUrl])

  return (
    <>
      <KakaoContainer>
        <LinkButton
          // id를 kakao-link-btn으로 설정
          id="kakao-link-btn"
        >
          <ImageBox>
            <Image
              src="/icons/kakao.png"
              width={20}
              height={10}
              alt="카톡 공유 이미지"
            />
          </ImageBox>
          <TextBox>공유하기</TextBox>
        </LinkButton>
      </KakaoContainer>
    </>
  )
}
