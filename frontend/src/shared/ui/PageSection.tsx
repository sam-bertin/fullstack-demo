import type { PropsWithChildren } from 'react'

type PageSectionProps = PropsWithChildren<{
  title: string
}>

export function PageSection({ title, children }: PageSectionProps) {
  return (
    <section className="page-section">
      <h2>{title}</h2>
      {children}
    </section>
  )
}
