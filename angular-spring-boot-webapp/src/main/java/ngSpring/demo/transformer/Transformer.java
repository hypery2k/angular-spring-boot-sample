package ngSpring.demo.transformer;

import ngSpring.demo.exceptions.EntityNotFoundException;

import java.util.List;

/**
 * Commont DTO and Domain transformer API
 *
 * @author hypery2k
 */
public interface Transformer<Entity, DTO> {

    Entity transformToEntity(DTO dto) throws EntityNotFoundException;

    DTO transformToDTO(Entity entity);

    List<Entity> transformToEntities(List<DTO> dtoList) throws EntityNotFoundException;

    List<DTO> transformToDTOs(List<Entity> entityList);

}
