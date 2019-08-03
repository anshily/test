import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {QuestionComponent} from './question/question.component';
import {IonicModule} from '@ionic/angular';
import {FormsModule} from '@angular/forms';

@NgModule({
    declarations: [QuestionComponent],
    entryComponents: [],
    imports: [
        CommonModule,
        FormsModule,
        IonicModule
    ],
    exports: [QuestionComponent]
})
export class ShareModule {
}
