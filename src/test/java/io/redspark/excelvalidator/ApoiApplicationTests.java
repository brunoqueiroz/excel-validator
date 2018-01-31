 package io.redspark.excelvalidator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.stella.tinytype.CPF;
import io.redspark.excelvalidator.model.fields.address.AddressColumn;
import io.redspark.excelvalidator.model.fields.address.AddressComplementColumn;
import io.redspark.excelvalidator.model.fields.address.AddressNumberColumn;
import io.redspark.excelvalidator.model.fields.money.MoneyValidator;
import org.junit.Before;
import org.junit.Test;

import io.redspark.excelvalidator.adapter.TableAdapterImpl;
import io.redspark.excelvalidator.builders.CellBuilder;
import io.redspark.excelvalidator.model.Table;
import io.redspark.excelvalidator.model.fields.birthday.BirthdayColumn;
import io.redspark.excelvalidator.model.fields.cep.CepColumn;
import io.redspark.excelvalidator.model.fields.cnpj.CnpjColumn;
import io.redspark.excelvalidator.model.fields.common.TextColumn;
import io.redspark.excelvalidator.model.fields.cpf.CpfColumn;
import io.redspark.excelvalidator.model.fields.ddd.DDDColumn;
import io.redspark.excelvalidator.model.fields.email.EmailColumn;
import io.redspark.excelvalidator.model.fields.genre.GenreColumn;
import io.redspark.excelvalidator.model.fields.id.IdColumn;
import io.redspark.excelvalidator.model.fields.mobilenumber.MobileNumberBrazilColumn;
import io.redspark.excelvalidator.model.fields.name.NameColumn;
import io.redspark.excelvalidator.model.fields.rg.RgColumn;
import io.redspark.excelvalidator.model.fields.status.StatusColumn;
import io.redspark.excelvalidator.services.SheetValidatorService;

public class ApoiApplicationTests {

    private String path;

    @Before
    public void init(){
        this.path = "/Users/brunoqueiroz/Documents/upload/";
    }


    @Test
    public void testNameColumnRequiredField() throws IOException {

        FileInputStream spreadsheet = new FileInputStream("src/test/resources/sheet_company.xlsx");

        List<CellBuilder> cells = new ArrayList<CellBuilder>();

        cells.add(CellBuilder.builder(NameColumn.class, 2).required(true));

        TableAdapterImpl tableAdapterImpl = new TableAdapterImpl(spreadsheet, true);
        Table table = new Table(tableAdapterImpl, cells);

        table.validateFields();

        assertEquals("NOME", table.getAdapter().getHeaderNameOf(2));
        assertFalse(table.getLines().get(0).getCells().get(0).getError());
        assertTrue(table.getLines().get(1).getCells().get(0).getError());
        assertTrue(table.getLines().get(2).getCells().get(0).getError());
        assertFalse(table.getLines().get(3).getCells().get(0).getError());
        assertFalse(table.getLines().get(4).getCells().get(0).getError());

    }

    @Test
    public void testNameColumnOptionalField() throws IOException {

        FileInputStream spreadsheet = new FileInputStream("src/test/resources/sheet_company.xlsx");

        List<CellBuilder> cells = new ArrayList<CellBuilder>();

        cells.add(CellBuilder.builder(NameColumn.class, 2).required(false));

        TableAdapterImpl tableAdapterImpl = new TableAdapterImpl(spreadsheet, true);
        Table table = new Table(tableAdapterImpl, cells);

        table.validateFields();

        assertEquals("NOME", table.getAdapter().getHeaderNameOf(2));
        assertFalse(table.getLines().get(0).getCells().get(0).getError());
        assertFalse(table.getLines().get(1).getCells().get(0).getError());
        assertFalse(table.getLines().get(2).getCells().get(0).getError());
        assertFalse(table.getLines().get(3).getCells().get(0).getError());

    }


    @Test
    public void testSuccess() throws IOException {

        FileInputStream spreadsheet = new FileInputStream("src/test/resources/sheet_company_success.xlsx");

        List<CellBuilder> cells = new ArrayList<CellBuilder>();

        cells.add(CellBuilder.builder(NameColumn.class, 2).required(true));
        cells.add(CellBuilder.builder(CpfColumn.class, 6).required(true));

        TableAdapterImpl tableAdapterImpl = new TableAdapterImpl(spreadsheet, true);
        Table table = new Table(tableAdapterImpl, cells);

        table.validateFields();

        assertEquals("Bruno", table.getLines().get(0).getCellStringValueOf(0));
        assertEquals(10955924677L, table.getLines().get(0).getCellLongValueOf(1).longValue());

        assertEquals("Caio", table.getLines().get(1).getCellStringValueOf(0));
        assertEquals(96476135473L, table.getLines().get(1).getCellLongValueOf(1).longValue());


    }

    @Test
    public void testMoneyValidation() throws IOException {

        MoneyValidator moneyValidator = new MoneyValidator();
        assertEquals(false, moneyValidator.execute("R$ 123,00").getError());
        assertEquals(false, moneyValidator.execute("123,00").getError());
        assertEquals(false, moneyValidator.execute("1.0023,00").getError());
        assertEquals(true, moneyValidator.execute("").getError());
        assertEquals(false, moneyValidator.execute("1").getError());
        assertEquals(false, moneyValidator.execute("0").getError());
        assertEquals(true, moneyValidator.execute("1,2,4,24.24").getError());
        assertEquals(false, moneyValidator.execute("AFFS213,00").getError());
        assertEquals(false, moneyValidator.execute("1").getError());

    }

    @Test
    public void testCNPJColumn() throws IOException {

        FileInputStream spreadsheet = new FileInputStream("src/test/resources/sheet_company.xlsx");

        List<CellBuilder> cells = new ArrayList<CellBuilder>();

        cells.add(CellBuilder.builder(NameColumn.class, 2).required(true));
        cells.add(CellBuilder.builder(CpfColumn.class, 6).required(true));
        cells.add(CellBuilder.builder(CnpjColumn.class, 1).required(true));

        TableAdapterImpl tableAdapterImpl = new TableAdapterImpl(spreadsheet, true);
        Table table = new Table(tableAdapterImpl, cells);

        table.validateFields();

        assertEquals(table.getAdapter().getHeaderNameOf(1), "COD CLIENTE (CNPJ)");
        assertFalse(table.getLines().get(0).getCells().get(2).getError());
        assertFalse(table.getLines().get(1).getCells().get(2).getError());
        assertTrue(table.getLines().get(2).getCells().get(2).getError());
        assertFalse(table.getLines().get(3).getCells().get(2).getError());
        assertFalse(table.getLines().get(4).getCells().get(2).getError());

    }

    @Test
    public void testIDColumn() throws IOException {

        FileInputStream spreadsheet = new FileInputStream("src/test/resources/sheet_company.xlsx");

        List<CellBuilder> cells = new ArrayList<CellBuilder>();

        cells.add(CellBuilder.builder(IdColumn.class, 0).required(true));

        TableAdapterImpl tableAdapterImpl = new TableAdapterImpl(spreadsheet, true);
        Table table = new Table(tableAdapterImpl, cells);

        table.validateFields();

        assertEquals(table.getAdapter().getHeaderNameOf(0), "SEQUENCIA");
        assertFalse(table.getLines().get(0).getCells().get(0).getError());
        assertFalse(table.getLines().get(1).getCells().get(0).getError());
        assertTrue(table.getLines().get(2).getCells().get(0).getError());
        assertFalse(table.getLines().get(3).getCells().get(0).getError());
        assertTrue(table.getLines().get(4).getCells().get(0).getError());

    }

    @Test
    public void testGenreColumn() throws IOException {

        FileInputStream spreadsheet = new FileInputStream("src/test/resources/sheet_company.xlsx");

        List<CellBuilder> cells = new ArrayList<CellBuilder>();

        cells.add(CellBuilder.builder(GenreColumn.class, 3).required(true));

        TableAdapterImpl tableAdapterImpl = new TableAdapterImpl(spreadsheet, true);
        Table table = new Table(tableAdapterImpl, cells);

        table.validateFields();

        assertEquals("GENERO", table.getAdapter().getHeaderNameOf(3));
        assertFalse(table.getLines().get(0).getCells().get(0).getError());
        assertFalse(table.getLines().get(1).getCells().get(0).getError());
        assertTrue(table.getLines().get(2).getCells().get(0).getError());
        assertFalse(table.getLines().get(3).getCells().get(0).getError());
        assertTrue(table.getLines().get(4).getCells().get(0).getError());

    }

    @Test
    public void testBirthdayColumn() throws IOException {

        FileInputStream spreadsheet = new FileInputStream("src/test/resources/sheet_company.xlsx");

        List<CellBuilder> cells = new ArrayList<CellBuilder>();

        cells.add(CellBuilder.builder(BirthdayColumn.class, 4).required(true));

        TableAdapterImpl tableAdapterImpl = new TableAdapterImpl(spreadsheet, true);
        Table table = new Table(tableAdapterImpl, cells);

        table.validateFields();

        assertEquals("DATA NASCIMENTO", table.getAdapter().getHeaderNameOf(4));
        assertFalse(table.getLines().get(0).getCells().get(0).getError());
        assertTrue(table.getLines().get(1).getCells().get(0).getError());
        assertTrue(table.getLines().get(2).getCells().get(0).getError());
        assertFalse(table.getLines().get(3).getCells().get(0).getError());
        assertFalse(table.getLines().get(4).getCells().get(0).getError());

    }

    @Test
    public void testRGColumn() throws IOException {

        FileInputStream spreadsheet = new FileInputStream("src/test/resources/sheet_company.xlsx");

        List<CellBuilder> cells = new ArrayList<CellBuilder>();

        cells.add(CellBuilder.builder(RgColumn.class, 5).required(true));

        TableAdapterImpl tableAdapterImpl = new TableAdapterImpl(spreadsheet, true);
        Table table = new Table(tableAdapterImpl, cells);

        table.validateFields();

        assertEquals("RG", table.getAdapter().getHeaderNameOf(5));
        assertFalse(table.getLines().get(0).getCells().get(0).getError());
        assertFalse(table.getLines().get(1).getCells().get(0).getError());
        assertFalse(table.getLines().get(2).getCells().get(0).getError());
        assertTrue(table.getLines().get(3).getCells().get(0).getError());
        assertFalse(table.getLines().get(4).getCells().get(0).getError());
    }

    @Test
    public void testCEPColumn() throws IOException {

        FileInputStream spreadsheet = new FileInputStream("src/test/resources/sheet_company.xlsx");

        List<CellBuilder> cells = new ArrayList<CellBuilder>();

        cells.add(CellBuilder.builder(CepColumn.class, 7).required(true));

        TableAdapterImpl tableAdapterImpl = new TableAdapterImpl(spreadsheet, true);
        Table table = new Table(tableAdapterImpl, cells);

        table.validateFields();

        assertEquals("CEP", table.getAdapter().getHeaderNameOf(7));
        assertTrue(table.getLines().get(0).getCells().get(0).getError());
        assertFalse(table.getLines().get(1).getCells().get(0).getError());
        assertFalse(table.getLines().get(2).getCells().get(0).getError());
        assertTrue(table.getLines().get(3).getCells().get(0).getError());
        assertFalse(table.getLines().get(4).getCells().get(0).getError());
    }

    @Test
    public void testAddressColumn() throws IOException {

        FileInputStream spreadsheet = new FileInputStream("src/test/resources/sheet_company.xlsx");

        List<CellBuilder> cells = new ArrayList<CellBuilder>();

        cells.add(CellBuilder.builder(TextColumn.class, 8).required(true));

        TableAdapterImpl tableAdapterImpl = new TableAdapterImpl(spreadsheet, true);
        Table table = new Table(tableAdapterImpl, cells);

        table.validateFields();

        assertEquals("ENDERECO", table.getAdapter().getHeaderNameOf(8));
        assertFalse(table.getLines().get(0).getCells().get(0).getError());
        assertFalse(table.getLines().get(1).getCells().get(0).getError());
        assertFalse(table.getLines().get(2).getCells().get(0).getError());
        assertFalse(table.getLines().get(3).getCells().get(0).getError());
        assertFalse(table.getLines().get(4).getCells().get(0).getError());
    }

    @Test
    public void testNumberColumn() throws IOException {

        FileInputStream spreadsheet = new FileInputStream("src/test/resources/sheet_company.xlsx");

        List<CellBuilder> cells = new ArrayList<CellBuilder>();

        cells.add(CellBuilder.builder(TextColumn.class, 9).required(true));

        TableAdapterImpl tableAdapterImpl = new TableAdapterImpl(spreadsheet, true);
        Table table = new Table(tableAdapterImpl, cells);

        table.validateFields();

        assertEquals("NUMERO", table.getAdapter().getHeaderNameOf(9));
        assertFalse(table.getLines().get(0).getCells().get(0).getError());
        assertFalse(table.getLines().get(1).getCells().get(0).getError());
        assertFalse(table.getLines().get(2).getCells().get(0).getError());
        assertTrue(table.getLines().get(3).getCells().get(0).getError());
        assertFalse(table.getLines().get(4).getCells().get(0).getError());
    }

    @Test
    public void testComplementColumnOptionalField() throws IOException {

        FileInputStream spreadsheet = new FileInputStream("src/test/resources/sheet_company.xlsx");

        List<CellBuilder> cells = new ArrayList<CellBuilder>();

        cells.add(CellBuilder.builder(TextColumn.class, 10).required(false));

        TableAdapterImpl tableAdapterImpl = new TableAdapterImpl(spreadsheet, true);
        Table table = new Table(tableAdapterImpl, cells);

        table.validateFields();

        assertEquals("COMPLEMENTO", table.getAdapter().getHeaderNameOf(10));
        assertFalse(table.getLines().get(0).getCells().get(0).getError());
        assertFalse(table.getLines().get(1).getCells().get(0).getError());
        assertFalse(table.getLines().get(2).getCells().get(0).getError());
        assertFalse(table.getLines().get(3).getCells().get(0).getError());
        assertFalse(table.getLines().get(4).getCells().get(0).getError());
    }

    @Test
    public void testEmailColumnField() throws IOException {

        FileInputStream spreadsheet = new FileInputStream("src/test/resources/sheet_company.xlsx");

        List<CellBuilder> cells = new ArrayList<CellBuilder>();

        cells.add(CellBuilder.builder(EmailColumn.class, 11).required(true));

        TableAdapterImpl tableAdapterImpl = new TableAdapterImpl(spreadsheet, true);
        Table table = new Table(tableAdapterImpl, cells);

        table.validateFields();

        assertEquals("EMAIL", table.getAdapter().getHeaderNameOf(11));
        assertFalse(table.getLines().get(0).getCells().get(0).getError());
        assertTrue(table.getLines().get(1).getCells().get(0).getError());
        assertTrue(table.getLines().get(2).getCells().get(0).getError());
        assertTrue(table.getLines().get(3).getCells().get(0).getError());
        assertFalse(table.getLines().get(4).getCells().get(0).getError());
    }

    @Test
    public void testDDDColumn() throws IOException {

        FileInputStream spreadsheet = new FileInputStream("src/test/resources/sheet_company.xlsx");

        List<CellBuilder> cells = new ArrayList<CellBuilder>();

        cells.add(CellBuilder.builder(DDDColumn.class, 12).required(true));

        TableAdapterImpl tableAdapterImpl = new TableAdapterImpl(spreadsheet, true);
        Table table = new Table(tableAdapterImpl, cells);

        table.validateFields();

        assertEquals("DDD", table.getAdapter().getHeaderNameOf(12));
        assertFalse(table.getLines().get(0).getCells().get(0).getError());
        assertFalse(table.getLines().get(1).getCells().get(0).getError());
        assertTrue(table.getLines().get(2).getCells().get(0).getError());
        assertTrue(table.getLines().get(3).getCells().get(0).getError());
        assertTrue(table.getLines().get(4).getCells().get(0).getError());
    }

    @Test
    public void testMobileNumberBrazilColumn() throws IOException {

        FileInputStream spreadsheet = new FileInputStream("src/test/resources/sheet_company.xlsx");

        List<CellBuilder> cells = new ArrayList<CellBuilder>();

        cells.add(CellBuilder.builder(MobileNumberBrazilColumn.class, 13).required(true));

        TableAdapterImpl tableAdapterImpl = new TableAdapterImpl(spreadsheet, true);
        Table table = new Table(tableAdapterImpl, cells);

        table.validateFields();

        assertEquals("TELEFONE CEL", table.getAdapter().getHeaderNameOf(13));
        assertTrue(table.getLines().get(0).getCells().get(0).getError());
        assertTrue(table.getLines().get(1).getCells().get(0).getError());
        assertTrue(table.getLines().get(2).getCells().get(0).getError());
        assertFalse(table.getLines().get(3).getCells().get(0).getError());
        assertFalse(table.getLines().get(4).getCells().get(0).getError());
    }

    @Test
    public void testStatusColumn() throws IOException {

        FileInputStream spreadsheet = new FileInputStream("src/test/resources/sheet_company.xlsx");

        List<CellBuilder> cells = new ArrayList<CellBuilder>();

        cells.add(CellBuilder.builder(StatusColumn.class, 14).required(true));

        TableAdapterImpl tableAdapterImpl = new TableAdapterImpl(spreadsheet, true);
        Table table = new Table(tableAdapterImpl, cells);

        table.validateFields();

        assertEquals("STATUS", table.getAdapter().getHeaderNameOf(14));
        assertTrue(table.getLines().get(0).getCells().get(0).getError());
        assertFalse(table.getLines().get(1).getCells().get(0).getError());
        assertTrue(table.getLines().get(2).getCells().get(0).getError());
        assertTrue(table.getLines().get(3).getCells().get(0).getError());
        assertFalse(table.getLines().get(4).getCells().get(0).getError());
    }

    @Test
    public void tableSize() throws IOException {

        FileInputStream spreadsheet = new FileInputStream("src/test/resources/sheet_company.xlsx");

        List<CellBuilder> cells = new ArrayList<CellBuilder>();

        cells.add(CellBuilder.builder(NameColumn.class, 2).required(true));
        cells.add(CellBuilder.builder(CpfColumn.class, 6).required(true));
        cells.add(CellBuilder.builder(CnpjColumn.class, 1).required(true));

        TableAdapterImpl tableAdapterImpl = new TableAdapterImpl(spreadsheet, true);
        Table table = new Table(tableAdapterImpl, cells);

        table.validateFields();

        assertEquals(table.getLines().size(), 5);

    }

    @Test
    public void testValidations() throws IOException {

        FileInputStream spreadsheet = new FileInputStream("src/test/resources/sheet_many_errors.xlsx");

        List<CellBuilder> cells = new ArrayList<>();
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

        TableAdapterImpl tableAdapterImpl = new TableAdapterImpl(spreadsheet, true);
        Table table = new Table(tableAdapterImpl, cells);

        table.validateFields();
        table.getAdapter().createSheet(this.path);

        assertEquals(table.getLines().size(), 5);

    }

}
