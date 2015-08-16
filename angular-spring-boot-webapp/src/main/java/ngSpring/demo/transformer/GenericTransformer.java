package ngSpring.demo.transformer;

import ngSpring.demo.exceptions.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Commont DTO and Domain transformer methods used by any transformer
 *
 * @author hypery2k
 */
public abstract class GenericTransformer<Entity, DTO> implements Transformer<Entity, DTO> {

    @Override
    public List<Entity> transformToEntities(List<DTO> dtoList) throws EntityNotFoundException {
        List<Entity> entityList = new ArrayList<Entity>();
        for (DTO dto : dtoList) {
            entityList.add(transformToEntity(dto));
        }
        return entityList;
    }

    @Override
    public List<DTO> transformToDTOs(List<Entity> entityList) {
        List<DTO> dtoList = new ArrayList<DTO>();
        for (Entity entity : entityList) {
            dtoList.add(transformToDTO(entity));
        }
        return dtoList;
    }
}
