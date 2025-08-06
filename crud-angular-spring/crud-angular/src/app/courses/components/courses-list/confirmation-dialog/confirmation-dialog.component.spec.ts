import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { ConfirmationDialogComponent } from './confirmation-dialog.component';

describe('ConfirmationDialogComponent', () => {
  let component: ConfirmationDialogComponent;
  let fixture: ComponentFixture<ConfirmationDialogComponent>;
  let mockDialogRef: jasmine.SpyObj<MatDialogRef<ConfirmationDialogComponent>>;
  const mockDialogData = 'Tem certeza que deseja continuar?';

  beforeEach(async () => {
    mockDialogRef = jasmine.createSpyObj('MatDialogRef', ['close']);

    await TestBed.configureTestingModule({
      imports: [MatDialogModule, MatButtonModule],
      providers: [
        { provide: MatDialogRef, useValue: mockDialogRef },
        { provide: MAT_DIALOG_DATA, useValue: mockDialogData }
      ],
      declarations: [ConfirmationDialogComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(ConfirmationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should close the dialog with "true" when the "Yes" button is clicked', () => {
    // Busca todos os botões no DOM de teste
    const buttons = fixture.nativeElement.querySelectorAll('button');
    // Assume que o botão 'Yes' é o primeiro
    const yesButton = buttons[0];

    if (yesButton) {
      yesButton.click();
      // O método onConfirm chama o close do MatDialogRef
      expect(mockDialogRef.close).toHaveBeenCalledWith(true);
    } else {
      fail('O botão "Yes" não foi encontrado no template.');
    }
  });

  it('should close the dialog with "false" when the "No" button is clicked', () => {
    const buttons = fixture.nativeElement.querySelectorAll('button');
    const noButton = buttons[1];

    if (noButton) {
      noButton.click();
      expect(mockDialogRef.close).toHaveBeenCalledWith(false);
    } else {
      fail('O botão "No" não foi encontrado no template.');
    }
  });
});
