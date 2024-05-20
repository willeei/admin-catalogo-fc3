package br.com.williamsbarriquero.admin.catalogo.application;

public abstract class UseCase<I, O> {

    public abstract O execute(I anId);
}