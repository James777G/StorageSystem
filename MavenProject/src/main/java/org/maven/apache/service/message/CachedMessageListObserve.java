package org.maven.apache.service.message;

import org.maven.apache.mapper.MessageMapper;
import org.maven.apache.message.Message;
import org.maven.apache.utils.MessageCachedUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("cachedMessageDataListService")
public class CachedMessageListObserve implements CachedMessageDataListService {


    @Override
    public void updateAllLists(MessageMapper MessageMapper, MessageManipulationService messageManipulationService) {
        List<Message> allMessageDESC = MessageMapper.selectAll();
        List<Message> starMessageDESC = allMessageDESC.stream()
                .filter(p -> p.getStar() == 1).collect(Collectors.toList());
        List<List<Message>> allMessage = messageManipulationService.getPagedCacheList(allMessageDESC, 5);
        List<List<Message>> starMessage = messageManipulationService.getPagedCacheList(starMessageDESC, 5);
        MessageCachedUtils.putLists(MessageCachedUtils.listType.All_MESSAGE, allMessage);
        MessageCachedUtils.putLists(MessageCachedUtils.listType.STAR_MESSAGE, starMessage);
    }
}
