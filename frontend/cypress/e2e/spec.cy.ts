describe('template spec', () => {
  it('passes', () => {
    cy.visit('http://localhost:3000')
    cy.wait(1000)
    cy.get('#loginBtn').click()
    cy.get('#username').type("omuletzu")
    cy.get('#password').type("root")
    cy.get('#radix-«R4fbl7» > .inline-flex').click()
    cy.get('[aria-controls="radix-«r0»"]').click()
    cy.get('#title').type("Postare")
    cy.get('#text').type("Descriere 1")
    cy.get('#addQ').click()
  })
})