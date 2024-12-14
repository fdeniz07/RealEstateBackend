package com.prettier.payloads.mapper;

import com.prettier.config.MapStructConfig;
import com.prettier.entity.concretes.Message;
import com.prettier.payloads.request.concretes.NewMessageRequest;
import com.prettier.payloads.response.concretes.MessageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = MapStructConfig.class)
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    @Mapping(target = "receiver.email", source = "receiverMail")
    Message toMessage(NewMessageRequest messageRequest);

    @Mapping(target = "receiverMail", source = "receiver.email")
    @Mapping(target = "senderMail", source = "sender.email")
    MessageResponse toResponse(Message message);

}
