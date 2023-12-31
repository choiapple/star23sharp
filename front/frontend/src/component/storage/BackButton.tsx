'use client'

import { useRouter } from 'next/navigation'
import { BackBtnWrapper, CustomImage } from './storageComponent.styled'

export default function BackButton() {
  const router = useRouter()

  const handleClick = () => {
    router.back()
  }

  return (
    <BackBtnWrapper>
      <CustomImage
        className=" m-2 h-7 w-7 object-contain hover:opacity-50"
        src="/icons/BackBtn.png"
        onClick={handleClick}
      />
    </BackBtnWrapper>
  )
}
