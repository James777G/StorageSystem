package org.maven.apache.service.regulatory;

import org.maven.apache.exception.DataNotFoundException;
import org.maven.apache.item.Item;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.mapper.RegulatoryMapper;
import org.maven.apache.regulatory.Regulatory;
import org.springframework.stereotype.Service;

@Service("regulatoryItemStrategy")
public final class ItemRegulatoryStrategy extends AbstractRegulatoryStrategy {

    @Override
    public boolean doStrategy(Regulatory regulatory, ItemMapper itemMapper, RegulatoryMapper regulatoryMapper) throws DataNotFoundException {
        String itemName = regulatory.getItemName();
        if(isValid(itemMapper,itemName)){
            regulatoryMapper.addNewRegulatory(regulatory);
            return true;
        } else{
            throw new DataNotFoundException("This Item Does Not Exit In The Database");
        }
    }

    private boolean isValid(ItemMapper itemMapper, String itemName){
        try{
            Item item = itemMapper.selectByItemName(itemName);
            return item != null;
        } catch (Exception e){
            return false;
        }
    }
}
