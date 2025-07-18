import { Injectable } from '@angular/core';
import { FormGroup, UntypedFormGroup, UntypedFormArray, UntypedFormControl, FormArray } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class FormUtilsService {

  constructor() { }

  validadeAllFormFields(formGroup: UntypedFormGroup | UntypedFormArray ){
    Object.keys(formGroup.controls).forEach(field =>{
      const control = formGroup.get(field);

      if(control instanceof UntypedFormControl){
        control.markAsTouched({onlySelf: true});
      } else if(control instanceof FormGroup || control instanceof FormArray){
        control.markAsTouched({onlySelf: true});
        this.validadeAllFormFields(control);
      }
    })

  }



  getErrorMessage(FormGroup: UntypedFormGroup, fieldName: string){
    const field = FormGroup.get(fieldName) as UntypedFormControl;

    return this.getErrorMessageFromField(field);
  }

  getErrorMessageFromField(field: UntypedFormControl){

    if(field?.hasError('required')){
      return 'Required Field'
    }

    if(field?.hasError('minlength')){
      const requiredLength = field.errors?field.errors['minlength']['requiredLength'] : 5;
      return `The minimum size must be ${requiredLength} characters.`
    }

    if(field?.hasError('maxlength')){
      const requiredLength = field.errors?field.errors['maxlength']['requiredLength'] : 200;
      return `The maximum size must be ${requiredLength} characters.`
    }

    return 'Invalid fields.'
  }

  getFormArrayFieldErrorMessage(formGroup: UntypedFormGroup, formArrayName: string,
    fieldName: string, index: number){
    const formArray = formGroup.get(formArrayName) as UntypedFormArray;
    const field = formArray.controls[index].get(fieldName) as UntypedFormControl;
    return this.getErrorMessageFromField(field);
  }

  isFormArrayRequired(formGroup: UntypedFormGroup, formArrayName: string){
    const formArray = formGroup.get(formArrayName) as UntypedFormArray;
    return !formArray.valid && formArray.hasError('required') && formArray.touched;
  }
}
