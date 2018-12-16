import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'findLanguageFromKey' })
export class FindLanguageFromKeyPipe implements PipeTransform {
    private languages: any = {
        'zh-tw': { name: '繁體中文' },
        en: { name: 'English' },
        hi: { name: 'हिंदी' },
        ru: { name: 'Русский' },
        es: { name: 'Español' }
        // jhipster-needle-i18n-language-key-pipe - JHipster will add/remove languages in this object
    };
    transform(lang: string): string {
        return this.languages[lang].name;
    }
}
