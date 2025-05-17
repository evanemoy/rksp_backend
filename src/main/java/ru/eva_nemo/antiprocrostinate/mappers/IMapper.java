package ru.eva_nemo.antiprocrostinate.mappers;

public interface IMapper<D, E> {
  D mapToDto(E e);
  E mapToEntity(D e);
}
