'use client'

import styled from 'styled-components'

export const LetterBoard = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`
export const LetterContainer = styled.div`
  display: flex;
  flex-direction: column;
  position: relative;
  box-shadow: 0px 4px 1px rgba(0, 0, 0, 0.25);
  background-color: rgba(255, 255, 255, 1);

  // min-height: 20rem;

  border-radius: 0.625rem;
  padding: 1rem;
`

export const DecoBottonWrapper = styled.div`
  display: flex;
  flex-direction: row;
  gap: 0.25rem;

  margin-bottom: 1rem;
  align-self: flex-start;
  justify-self: start;
`
const LetterDecoBotton = styled.div`
  border-radius: 500px;
  width: 1.5rem;
  height: 1.5rem;
`
export const RedDecoBotton = styled(LetterDecoBotton)`
  box-shadow: 0px 4px 0px
    rgba(211.43749594688416, 167.57891178131104, 160.34010261297226, 1);
  background-color: rgba(255, 152.24383145570755, 134.93749290704727, 1);
`
export const YellowDecoBotton = styled(LetterDecoBotton)`
  box-shadow: 0px 4px 0px
    rgba(221.00000202655792, 206.0000029206276, 164.00000542402267, 1);
  background-color: rgba(255, 224.59425956010818, 137.06250607967377, 1);
`
export const GreenDecoBotton = styled(LetterDecoBotton)`
  box-shadow: 0px 4px 0px
    rgba(173.11667382717133, 221.00000202655792, 175.61491817235947, 1);
  background-color: rgba(
    196.3987085223198,
    254.7875154018402,
    199.179125726223,
    1
  );
`

export const MediaWrapper = styled.div`
  overflow: hidden;
  border-radius: 5px;
  margin-bottom: 1rem;
  background-color: #f2f2f2;

  display: flex;
  justify-content: center;
  align-items: center;
  padding: 1rem;
`
export const CustomImage = styled.img``

export const LetterContent = styled.div`
  width: 100%;
  margin-top: 0.5rem;
  // background-image: url('/icons/RandomLetterBG.png');
  box-shadow: inset 0px 4px 1px rgba(0, 0, 0, 0.25);

  background-color: rgba(
    250.00000029802322,
    247.00000047683716,
    240.00000089406967,
    1
  );
  height: 100%;
  max-height: 12rem;
  overflow-y: scroll;

  word-wrap: break-word;

  // background-image: repeating-linear-gradient(
  //   rgba(250.00000029802322, 247.00000047683716, 240.00000089406967, 1),
  //   rgba(250.00000029802322, 247.00000047683716, 240.00000089406967, 1) 34px,
  //   lightgray 34px,
  //   lightgray 36px
  // );
  line-height: 36px;
  padding: 8px 10px;
  outline: none;

  // padding: 1rem;
  border-radius: 0.5rem;
`
export const Hint = styled.p`
  margin-top: 0.5rem;
  font-weight: bold;
  color: #ff6045;
`
export const Date = styled.p`
  font-size: 1rem;
  text-align: end;
  color: #6a6a6a;
`

export const PreviewImg = styled.img``

export const PreviewMovie = styled.video``

export const PreviewAudio = styled.audio`
  width: 100%;
`

export const CloseBotton = styled.button`
  box-shadow: 0px 4px 1px rgba(0, 0, 0, 0.25);
  background-color: rgba(
    61.834769770503044,
    60.64422145485878,
    130.88671267032623,
    1
  );
  width: 6rem;
  height: 2.3rem;
  border-radius: 1.25rem;

  color: white;
  font-size: 1.2rem;

  margin-top: 1rem;
  align-self: flex-end;
  justify-self: end;
`
