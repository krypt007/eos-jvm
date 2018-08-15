package com.memtrip.eosio.gen;

import com.google.googlejavaformat.java.FormatterException;
import com.memtrip.eosio.FreeMarker;
import com.memtrip.eosio.SourceFileGenerator;
import com.memtrip.eosio.model.AbiModel;

import java.io.IOException;
import java.util.List;

public class Generate {

    private final List<AbiModel> abiModelList;
    private final FreeMarker freeMarker;
    private final SourceFileGenerator sourceFileGenerator;

    public Generate(List<AbiModel> abiModelList, FreeMarker freeMarker, SourceFileGenerator sourceFileGenerator) {
        this.abiModelList = abiModelList;
        this.freeMarker = freeMarker;
        this.sourceFileGenerator = sourceFileGenerator;
    }

    public void generate() throws IOException, FormatterException {
        new RootGen(new RootMap(abiModelList), freeMarker, sourceFileGenerator).write();

        for (AbiModel abiModel : abiModelList) {
            new SquishableGen(
                new SquishableMap(abiModel),
                abiModel.getClassName() + "Squishable",
                freeMarker,
                sourceFileGenerator
            ).write();
        }
    }
}
