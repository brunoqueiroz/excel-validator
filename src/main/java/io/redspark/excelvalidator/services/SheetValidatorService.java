package io.redspark.excelvalidator.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.redspark.excelvalidator.adapter.TableAdapterImpl;
import io.redspark.excelvalidator.builders.CellBuilder;
import io.redspark.excelvalidator.model.Table;
import io.redspark.excelvalidator.model.fields.address.AddressColumn;
import io.redspark.excelvalidator.model.fields.address.AddressComplementColumn;
import io.redspark.excelvalidator.model.fields.address.AddressNumberColumn;
import io.redspark.excelvalidator.model.fields.birthday.BirthdayColumn;
import io.redspark.excelvalidator.model.fields.cep.CepColumn;
import io.redspark.excelvalidator.model.fields.cnpj.CnpjColumn;
import io.redspark.excelvalidator.model.fields.cpf.CpfColumn;
import io.redspark.excelvalidator.model.fields.ddd.DDDColumn;
import io.redspark.excelvalidator.model.fields.email.EmailColumn;
import io.redspark.excelvalidator.model.fields.genre.GenreColumn;
import io.redspark.excelvalidator.model.fields.id.IdColumn;
import io.redspark.excelvalidator.model.fields.mobilenumber.MobileNumberBrazilColumn;
import io.redspark.excelvalidator.model.fields.name.NameColumn;
import io.redspark.excelvalidator.model.fields.rg.RgColumn;
import io.redspark.excelvalidator.model.fields.status.StatusColumn;

public class SheetValidatorService {

    private static final String NEW_FILE_NAME = "/Users/brunoqueiroz/Documents/upload/";

    public void execute(InputStream file) throws IOException {

        List<CellBuilder> cells = new ArrayList<CellBuilder>();

        cells.add(CellBuilder.builder(IdColumn.class, 0).required(true));
        cells.add(CellBuilder.builder(CnpjColumn.class, 1).required(true));
        cells.add(CellBuilder.builder(NameColumn.class, 2).required(true));
        cells.add(CellBuilder.builder(GenreColumn.class, 3).required(true));
        cells.add(CellBuilder.builder(BirthdayColumn.class, 4).required(true));
        cells.add(CellBuilder.builder(RgColumn.class, 5).required(true));
        cells.add(CellBuilder.builder(CpfColumn.class, 6).required(true));
        cells.add(CellBuilder.builder(CepColumn.class, 7).required(true));
        cells.add(CellBuilder.builder(AddressColumn.class, 8).required(true));
        cells.add(CellBuilder.builder(AddressNumberColumn.class, 9).required(true));
        cells.add(CellBuilder.builder(AddressComplementColumn.class, 10).required(true));
        cells.add(CellBuilder.builder(EmailColumn.class, 11).required(true));
        cells.add(CellBuilder.builder(DDDColumn.class, 12).required(true));
        cells.add(CellBuilder.builder(MobileNumberBrazilColumn.class, 13).required(true));
        cells.add(CellBuilder.builder(StatusColumn.class, 14).required(true));

        TableAdapterImpl tableAdapterImpl = new TableAdapterImpl(file, true);
        Table table = new Table(tableAdapterImpl, cells);

        table.validateFields();
        table.getAdapter().createSheet(NEW_FILE_NAME);

    }


}
