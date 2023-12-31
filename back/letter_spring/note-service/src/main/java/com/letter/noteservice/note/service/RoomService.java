package com.letter.noteservice.note.service;

import com.letter.noteservice.exception.BusinessLogicException;
import com.letter.noteservice.exception.ExceptionCode;
import com.letter.noteservice.note.dto.RoomDto;
import com.letter.noteservice.note.entity.Note;
import com.letter.noteservice.note.entity.Room;
import com.letter.noteservice.note.feign.AuthFeignClient;
import com.letter.noteservice.note.repository.NoteRepository;
import com.letter.noteservice.note.repository.RoomRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;
    private final NoteRepository noteRepository;
    private final AuthFeignClient authFeignClient;


    /**
     * 쪽지 방 목록 조회
     */
    public List<RoomDto.RoomListResDto> roomList(HttpServletRequest request) {
        // 요청 헤더에서 멤버 유니크 변수로 저장
        String phone = request.getHeader("Member-Authorization-Phone");
        System.out.println("phone >>> " + phone);

        // 인증 서버에서 유저 id 가져오기
        Long memberId = authFeignClient.findMemberId(phone);

        List<Room> roomList = roomRepository.findAllByRoom(memberId);
        List<RoomDto.RoomListResDto> roomListResDtos = new ArrayList<>();
        for (Room room : roomList) {

            RoomDto.RoomListResDto roomDto = RoomDto.RoomListResDto.builder()
                    .id(room.getId())
                    .senderId(room.getSenderId())
                    .senderName(room.getSenderName())
                    .receiverId(room.getReceiverId())
                    .receiverName(room.getReceiverName())
                    .createdAt(room.getCreatedAt())
                    .build();

            // 보낸 사람은 store 가 false 라도 볼수 있고, 받는 사람은 보이면 안된다.

            Optional<Note> lastNote = noteRepository.findTopByRoomIdAndReceiverIdOrRoomIdAndSenderIdOrderByCreatedAtDesc(room.getId(), memberId, room.getId(), memberId);
            System.out.println("lastNote >>> " + " " + room.getId() + " " +  lastNote.get().getSenderId() + " " + lastNote.get().getSenderName() + " " + lastNote.get().getIsStore() + " " + lastNote.get().getRoom().getId());
            if (lastNote.isEmpty()) continue;
            else if (room.getIsFirst() && !lastNote.get().getSenderId().equals(memberId) && !lastNote.get().getIsStore()) continue;

            // 쪽지 읽음 여부 처리
            Optional<Note> readNote = noteRepository.findTopByRoomIdAndReceiverIdAndIsReadTrueAndIsStoreTrueOrderByCreatedAtDesc(room.getId(), memberId);
            if (readNote.isPresent()) roomDto.setIsRead(true);
            else roomDto.setIsRead(false);

            // 쪽지 답장 여부 처리
            Optional<Note> replyNote = noteRepository.findTopByRoomIdAndReceiverIdOrderByCreatedAtDesc(room.getId(), memberId);
            if (replyNote.isEmpty()) roomDto.setIsReply(false);
            else if (replyNote.get().getReceiverId() == memberId && replyNote.get().getIsRead()) roomDto.setIsReply(true);
            else roomDto.setIsReply(false);

            roomListResDtos.add(roomDto);
        }

        return roomListResDtos;
    }

    /**
     * 오늘 온 쪽지 방 목록 조회
     */
    public RoomDto.RoomListTodayResDto roomListToday(HttpServletRequest request) {
        // 요청 헤더에서 멤버 유니크 변수로 저장
        String phone = request.getHeader("Member-Authorization-Phone");
        System.out.println("phone >>> " + phone);

        // 인증 서버에서 유저 id 가져오기
        Long memberId = authFeignClient.findMemberId(phone);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.minusDays(1).withHour(17).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = now.withHour(16).withMinute(59).withSecond(59).withNano(0);

        System.out.println("Data >>> " + start + " " + end);

        List<Room> roomList = roomRepository.findAllByReceiverIdOrSenderId(memberId, memberId);

        int count = 0;
        List<RoomDto.RoomListResDto> roomListResDtos = new ArrayList<>();
        for (Room room : roomList) {
            // 각 쪽지 방에서 최신 쪽지가 오늘 왔는지 확인
            Optional<Note> note = noteRepository.findTopByRoomIdAndReceiverIdAndIsReadFalseAndIsStoreTrueAndCreatedAtBetweenOrderByCreatedAtDesc(room.getId(), memberId, start, end);
            if (note.isEmpty()) continue;

            RoomDto.RoomListResDto roomDto = RoomDto.RoomListResDto.builder()
                    .id(room.getId())
                    .senderId(room.getSenderId())
                    .senderName(room.getSenderName())
                    .receiverId(room.getReceiverId())
                    .receiverName(room.getReceiverName())
                    .createdAt(room.getCreatedAt())
                    .build();


            // 쪽지 읽음 여부 처리
            Optional<Note> readNote = noteRepository.findTopByRoomIdAndReceiverIdAndIsReadTrueAndIsStoreTrueOrderByCreatedAtDesc(room.getId(), memberId);
            if (readNote.isPresent()) roomDto.setIsRead(true);
            else roomDto.setIsRead(false);

            // 쪽지 답장 여부 처리
            Optional<Note> replyNote = noteRepository.findTopByRoomIdAndReceiverIdOrderByCreatedAtDesc(room.getId(), memberId);
            if (replyNote.isEmpty()) roomDto.setIsReply(false);
            else if (replyNote.get().getReceiverId() == memberId && replyNote.get().getIsRead()) roomDto.setIsReply(true);
            else roomDto.setIsReply(false);

            count++;

            roomListResDtos.add(roomDto);
        }

        return RoomDto.RoomListTodayResDto.builder()
                .memberId(memberId)
                .count(count)
                .roomListResDtoList(roomListResDtos)
                .build();
    }


    /**
     * 쪽지 방 상세 조회
     */
    public Room roomDetail(HttpServletRequest request, Long roomId) {
        // 요청 헤더에서 멤버 유니크 변수로 저장
        String phone = request.getHeader("Member-Authorization-Phone");
        System.out.println("phone >>> " + phone);

        // 인증 서버에서 유저 id 가져오기
        Long memberId = authFeignClient.findMemberId(phone);

        Room room = roomRepository.findById(roomId).get();

        // 상세 조회시 모든 쪽지 읽음 처리
//        List<Note> noteList = noteRepository.findByLastNote(roomId, memberId);
//        for (Note note: noteList) {
//            System.out.println("note >>> " + note.toString());
//            if (! note.getRead()) updateRoomReadStatus(room);
//        }

        return room;
    }

    @Transactional
    public void updateRoomReadStatus(Room room) {
        room.setIsRead(false);
    }
}
